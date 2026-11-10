# Gaps checklist

Run every check; each row of the report is a gap with its evidence and a mark, verified or unverified.

| Check | How | What it shows |
|---|---|---|
| Uncovered Activity | `/api/uncovered-activity`, entries since the app started; attribute each by its frames: the app's packages, then the thread root (`ThreadPoolExecutor`, `ScheduledRunnable`, `StandardHostValve.status`, startup `main`) | async work, error-page forwards, startup |
| Capture-only trails | `/api/trails?service=<database system>`: trails whose `services` is the database alone; group by changed tables | writes no decision claims, usually async work |
| Status | decision `status` versus the trail list's status | 4xx rejected in the decision, listed as succeeded |
| Names | decisions named `GET /` | requests no handler mapped (404) |
| Transaction link | a writing decision without `reference-used` `database-transaction` | database too old, or not captured |
| Unrecorded stores | datastores from step 1 without observations | no support (for example MongoDB) |
| Correlation | follow-ups of one case (verify, pay) in separate trails | trails join by references only; the case search joins by row ids |

Attribute entries with a script, not by eye:

```python
# entries since <start>, outside other apps' packages; prints kind, the app's frames, the thread root
for e in entries:
    frames = e["trace"]["frames"]
    app = [f["class"].split(".")[-1] + "." + f["method"] for f in frames if "<app package>" in f["class"]][:3]
    root = [f["class"].split(".")[-1] + "." + f["method"] for f in frames][-3:]
    print(e["kind"], e["firstSeen"], app, root)
```

Report each gap as: what is missing, the evidence (an entry, a trail id, a probe's output), the cause, and the fix
(configuration, an app change for the user to decide, or an issue for Clarity).
