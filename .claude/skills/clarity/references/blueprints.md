# Business events

A blueprint is SQL views over Clarity's tables plus a mapping of views to event types (`docs/pipeline-sql.md`).
Keep both files in the app's repo, `<name>.sql` and `<name>.events.json`, and run them with
[`scripts/blueprint.py`](../scripts/blueprint.py) `preview|save <path>/<name>`.

## Steps

1. **Inventory the app's language.** Grep its event classes (domain events, published or external events, messages)
   and the words of its UI and API. Done when each event you plan has the app's name beside it, or "none".
2. **Model business facts.** One event for each thing that happened for the business, named in the app's words where
   those are business words. Leave mechanics out: an internal wallet, ledger lines, an outbox. A payment is one
   `PaymentCommitmentPaid`, not `MoneyDeposited` plus `CommitmentPaid` "with account funds". When derived events must
   stand in for the app's own events (a move to event sourcing), mirror the app one to one instead.
3. **Prove each fact from its row**: the row that states it (an inserted row, a status that flipped, `paid = true`),
   joined to its request through the transaction link for who and how. A fact read from a URL is a guess.
4. **Keep personal data out**: no names, e-mails, national ids or phone numbers. Tag every event of a case with the
   business id (`campParticipantId`), so one case reads as a timeline with the `tag=` filter.
5. **Preview and compare.** Preview over all batches, and compare the count of each event type with the source of
   truth (a `count(*)` in the app's database). Done when every count matches; then save with backfill.
6. **Change later**: a changed event view gets a new `version` (or name) and a save with backfill replays the whole
   history for it; unchanged types go on without duplicates. Old events stay; Clarity deletes none.

## SQL that works

The transaction link, the only way to join two decisions:

```sql
CREATE VIEW tx_link AS
    SELECT a.decision_id, b.decision_id AS tx_decision_id
    FROM decision_reference a
    JOIN decision_reference b
      ON b.ref_type = a.ref_type AND b.ref_system = a.ref_system AND b.ref_value = a.ref_value
     AND b.decision_id <> a.decision_id
    WHERE a.ref_type = 'database-transaction' AND a.part IS NOT NULL;
CREATE VIEW signed_in_user AS
    SELECT decision_id, data['name'] AS user_name FROM observation WHERE kind = 'http-user-authenticated';
```

## Gotchas (each seen on a real run)

- `decision_ended.outcome` is also `rejected` (4xx), not only `succeeded` and `failed`.
- `observation.data` is null for kinds with a table of their own, such as `decision-ended`: read the table.
- The HTTP status is text in `observation.data['status']` of kind `http-response-sent`.
- Row values are text: booleans `'true'`/`'false'`; enums may be ordinals (take the meaning from the table name);
  timestamps are microseconds (take `decision_ended.ended_at` as the event's time).
- Inner joins only, no aggregates, decisions joined only on reference keys; the save says which line is refused.
- Without `REPLICA IDENTITY FULL`, an update's `before_row` holds only the key: filter on `after_row`.
