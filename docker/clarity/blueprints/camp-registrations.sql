-- Business events of camp registrations, derived by Axoniq Clarity from what it recorded of bialydunajec: the backend's
-- requests (agent) and the rows they changed (change capture of bialydunajec-postgres-db). No names, e-mails, PESELs
-- or phone numbers go into the events. The SQL dialect: axoniq-clarity repo, docs/pipeline-sql.md.

-- An application decision and the database transaction it used, the only way to join two decisions.
CREATE VIEW tx_link AS
    SELECT a.decision_id, b.decision_id AS tx_decision_id
    FROM decision_reference a
    JOIN decision_reference b
      ON b.ref_type = a.ref_type AND b.ref_system = a.ref_system AND b.ref_value = a.ref_value
     AND b.decision_id <> a.decision_id
    WHERE a.ref_type = 'database-transaction' AND a.part IS NOT NULL;

-- A camper registered: the camp_participant row inserted, and the request that did it (the form or the dev endpoint).
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

-- The camp shirt a camper ordered with the registration.
CREATE VIEW camp_shirt_ordered AS
    SELECT r.after_row['aggregate_id'] AS shirt_order_id,
           r.after_row['camp_participant_id'] AS camp_participant_id,
           r.after_row['color_name'] AS color,
           r.after_row['size_name'] AS size,
           r.after_row['type'] AS cut,
           e.ended_at AS ordered_at,
           r.decision_id
    FROM row_changed r
    JOIN decision_ended e ON e.decision_id = r.decision_id
    WHERE r.schema_name = 'camp_registrations' AND r.table_name = 'shirt_order' AND r.op = 'c';

-- A camper confirmed the registration with the link of the e-mail.
CREATE VIEW camp_participant_registration_verified AS
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

-- Who was signed in to a request: the admin panel's user.
CREATE VIEW signed_in_user AS
    SELECT decision_id, data['name'] AS user_name FROM observation WHERE kind = 'http-user-authenticated';

-- An admin booked a payment of a camper: the deposit inserted, and the camper's account it went to, in one transaction.
CREATE VIEW camp_payment_booked AS
    SELECT a.after_row['camp_participant_id'] AS camp_participant_id,
           a.after_row['cottage_id'] AS cottage_id,
           CASE WHEN e.name LIKE '%/down-payment/payment' THEN 'DOWN_PAYMENT'
                WHEN e.name LIKE '%/camp-participation/payment' THEN 'CAMP_PARTICIPATION'
                WHEN e.name LIKE '%/camp-bus/payment' THEN 'CAMP_BUS_SEAT'
                ELSE 'OTHER' END AS payment_type,
           CAST(o.after_row['value'] AS DECIMAL(19, 2)) AS amount,
           o.after_row['currency'] AS currency,
           u.user_name AS booked_by,
           e.ended_at AS booked_at,
           e.decision_id, o.decision_id AS tx_decision_id
    FROM decision_ended e
    JOIN signed_in_user u ON u.decision_id = e.decision_id
    JOIN tx_link l ON l.decision_id = e.decision_id
    JOIN row_changed o ON o.decision_id = l.tx_decision_id
    JOIN row_changed a ON a.decision_id = l.tx_decision_id
    WHERE o.table_name = 'account_operation' AND o.op = 'c' AND o.after_row['type'] = 'DEPOSIT'
      AND a.table_name = 'camp_participant_cottage_account' AND a.op = 'u'
      AND e.name LIKE 'POST /rest-api/v1/admin/payment-commitment/%' AND e.outcome = 'succeeded';

-- A registration the backend refused, such as a PESEL that is registered already (400). The agent records no
-- response body, so the backend's error code (CAMP_PARTICIPANT_WITH_GIVEN_PESEL_IS_ALREADY_REGISTERED) is not here.
CREATE VIEW camp_participant_registration_rejected AS
    SELECT CAST(s.data['status'] AS INTEGER) AS http_status,
           e.ended_at AS rejected_at,
           e.decision_id
    FROM decision_ended e
    JOIN observation s ON s.decision_id = e.decision_id AND s.kind = 'http-response-sent'
    WHERE e.name = 'POST /rest-api/v1/camp-registrations/in-progress/camp-participant'
      AND e.outcome = 'rejected';

-- An e-mail the app tried to send, such as the confirmation of a registration. It runs on the app's RxJava threads,
-- outside any request, so it comes from change capture alone.
CREATE VIEW email_send_attempted AS
    SELECT r.after_row['aggregate_id'] AS email_message_id,
           r.after_row['subject'] AS subject,
           r.after_row['status'] AS status,
           e.ended_at AS attempted_at,
           r.decision_id
    FROM row_changed r
    JOIN decision_ended e ON e.decision_id = r.decision_id
    WHERE r.schema_name = 'email' AND r.table_name = 'email_message_log' AND r.op = 'c';
