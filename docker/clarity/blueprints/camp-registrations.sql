-- Business events of camp registrations, derived by Axoniq Clarity from what it recorded of bialydunajec: the backend's
-- requests (agent) and the rows they changed (change capture of bialydunajec-postgres-db). No names, e-mails, PESELs
-- or phone numbers go into the events. The SQL dialect: axoniq-clarity repo, docs/pipeline-sql.md.
--
-- One event for each thing that happened for the camp, in the app's words where they are business words (its
-- external events in bialydunajec-registrations/messages), not its mechanics: a payment is one PaymentCommitmentPaid,
-- not the cottage account's MoneyDeposited and CommitmentPaid "with account funds".

-- An application decision and the database transaction it used, the only way to join two decisions.
CREATE VIEW tx_link AS
    SELECT a.decision_id, b.decision_id AS tx_decision_id
    FROM decision_reference a
    JOIN decision_reference b
      ON b.ref_type = a.ref_type AND b.ref_system = a.ref_system AND b.ref_value = a.ref_value
     AND b.decision_id <> a.decision_id
    WHERE a.ref_type = 'database-transaction' AND a.part IS NOT NULL;

-- Who was signed in to a request: the admin panel's user.
CREATE VIEW signed_in_user AS
    SELECT decision_id, data['name'] AS user_name FROM observation WHERE kind = 'http-user-authenticated';

-- CampParticipantExternalEvent.CampParticipantRegistered: the camp_participant row inserted, and the request that did
-- it (the form or the dev endpoint).
CREATE VIEW camp_participant_registered AS
    SELECT r.after_row['aggregate_id'] AS camp_participant_id,
           r.after_row['camp_registrations_edition_id'] AS camp_edition_id,
           r.after_row['cottage_id'] AS cottage_id,
           r.after_row['gender'] AS gender,
           r.after_row['university'] AS university,
           r.after_row['field_of_study'] AS field_of_study,
           e.name AS registered_through,
           e.ended_at AS registered_at,
           e.decision_id, r.decision_id AS tx_decision_id
    FROM decision_ended e
    JOIN tx_link l ON l.decision_id = e.decision_id
    JOIN row_changed r ON r.decision_id = l.tx_decision_id
    WHERE r.schema_name = 'camp_registrations' AND r.table_name = 'camp_participant' AND r.op = 'c'
      AND e.outcome = 'succeeded';

-- ShirtOrderExternalEvent.OrderPlaced: the camp shirt ordered with the registration.
CREATE VIEW shirt_order_placed AS
    SELECT r.after_row['aggregate_id'] AS shirt_order_id,
           r.after_row['camp_participant_id'] AS camp_participant_id,
           r.after_row['color_name'] AS color,
           r.after_row['size_name'] AS size,
           r.after_row['type'] AS cut,
           e.ended_at AS placed_at,
           r.decision_id
    FROM row_changed r
    JOIN decision_ended e ON e.decision_id = r.decision_id
    WHERE r.schema_name = 'camp_registrations' AND r.table_name = 'shirt_order' AND r.op = 'c';

-- CampParticipantRegistrationEvent.VerifiedByCamper: the camper opened the link of the e-mail.
CREATE VIEW camp_participant_registration_verified_by_camper AS
    SELECT r.after_row['aggregate_id'] AS registration_id,
           r.after_row['camp_participant_id'] AS camp_participant_id,
           r.after_row['cottage_id'] AS cottage_id,
           e.ended_at AS verified_at,
           e.decision_id, r.decision_id AS tx_decision_id
    FROM decision_ended e
    JOIN tx_link l ON l.decision_id = e.decision_id
    JOIN row_changed r ON r.decision_id = l.tx_decision_id
    WHERE r.schema_name = 'camp_registrations' AND r.table_name = 'camp_participant_registration' AND r.op = 'u'
      AND r.after_row['status'] = 'VERIFIED_BY_CAMPER'
      AND e.name LIKE 'PATCH %/verification' AND e.outcome = 'succeeded';

-- CampParticipantExternalEvent.CampParticipantConfirmed: the same link confirms the participant, in a transaction of
-- its own.
CREATE VIEW camp_participant_confirmed AS
    SELECT r.after_row['aggregate_id'] AS camp_participant_id,
           r.after_row['cottage_id'] AS cottage_id,
           e.ended_at AS confirmed_at,
           e.decision_id, r.decision_id AS tx_decision_id
    FROM decision_ended e
    JOIN tx_link l ON l.decision_id = e.decision_id
    JOIN row_changed r ON r.decision_id = l.tx_decision_id
    WHERE r.schema_name = 'camp_registrations' AND r.table_name = 'camp_participant' AND r.op = 'u'
      AND r.after_row['participation_status'] = 'CONFIRMED_BY_CAMPER'
      AND e.name LIKE 'PATCH %/verification' AND e.outcome = 'succeeded';

-- PaymentCommitmentPaid: an admin booked a payment, and the camper's commitment (down payment, camp participation or
-- camp bus seat) became paid. The commitment row proves it; the camper's account in the same transaction says whose.
CREATE VIEW payment_commitment_paid AS
    SELECT a.after_row['camp_participant_id'] AS camp_participant_id,
           a.after_row['cottage_id'] AS cottage_id,
           CASE c.table_name WHEN 'camp_down_payment_commitment' THEN 'CAMP_DOWN_PAYMENT'
                             WHEN 'camp_participation_commitment' THEN 'CAMP_PARTICIPATION'
                             ELSE 'CAMP_BUS_SEAT' END AS commitment_type,
           CAST(c.after_row['amount_value'] AS DECIMAL(19, 2)) AS amount,
           c.after_row['amount_currency'] AS currency,
           u.user_name AS booked_by,
           e.ended_at AS paid_at,
           e.decision_id, c.decision_id AS tx_decision_id
    FROM decision_ended e
    JOIN signed_in_user u ON u.decision_id = e.decision_id
    JOIN tx_link l ON l.decision_id = e.decision_id
    JOIN row_changed c ON c.decision_id = l.tx_decision_id
    JOIN row_changed a ON a.decision_id = l.tx_decision_id
    WHERE c.table_name IN ('camp_down_payment_commitment', 'camp_participation_commitment', 'camp_bus_commitment')
      AND c.op = 'u' AND c.after_row['paid'] = 'true'
      AND a.table_name = 'camp_participant_cottage_account' AND a.op = 'u'
      AND c.after_row['entity_id'] IN (a.after_row['camp_down_payment_commitment_entity_id'],
                                       a.after_row['camp_participation_commitment_entity_id'],
                                       a.after_row['camp_bus_commitment_entity_id'])
      AND e.outcome = 'succeeded';

-- No event of the app: a registration the backend refused, such as a PESEL that is registered already (400). The
-- agent records no response body, so the backend's error code is not here.
CREATE VIEW camp_participant_registration_rejected AS
    SELECT CAST(s.data['status'] AS INTEGER) AS http_status,
           e.ended_at AS rejected_at,
           e.decision_id
    FROM decision_ended e
    JOIN observation s ON s.decision_id = e.decision_id AND s.kind = 'http-response-sent'
    WHERE e.name = 'POST /rest-api/v1/camp-registrations/in-progress/camp-participant'
      AND e.outcome = 'rejected';

-- EmailMessageLogEvent.EmailMessageSentSuccess and EmailMessageSentFailure: an e-mail, such as the confirmation of a
-- registration, logged as sent or failed. The app does it on its RxJava threads, outside any request, so it comes from
-- change capture alone.
CREATE VIEW email_message_logged AS
    SELECT r.after_row['aggregate_id'] AS email_message_id,
           r.after_row['subject'] AS subject,
           r.after_row['status'] AS status,
           e.ended_at AS logged_at,
           r.decision_id
    FROM row_changed r
    JOIN decision_ended e ON e.decision_id = r.decision_id
    WHERE r.schema_name = 'email' AND r.table_name = 'email_message_log' AND r.op = 'c';

CREATE VIEW email_message_sent AS
    SELECT email_message_id, subject, logged_at AS sent_at, decision_id FROM email_message_logged WHERE status = 'SENT';

CREATE VIEW email_message_sending_failed AS
    SELECT email_message_id, subject, logged_at AS failed_at, decision_id FROM email_message_logged
    WHERE status = 'FAIL_TO_SEND';
