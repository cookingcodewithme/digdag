# require>: Depends on another workflow

**require>** operator requires completion of another workflow. This operator is similar to [call> operator](../call.html), but this operator doesn't start the other workflow if it's already running or has done for the same session time of this workflow. If the workflow is running or newly started, this operator waits until it completes.

```
# workflow1.dig
+step1:
  require>: another_workflow
```

```
# another_workflow.dig
+step2:
  sh>: tasks/step2.sh
```


## Options

* **require>**: NAME

  Name of a workflow.

  Examples:

  ```
  require>: another_workflow
  ```

* **session_time**: ISO_TIME_WITH_ZONE

  Examples:

  ```
  require>: another_workflow
  session_time: 20170101T00:00:00+0000
  ```

  ```
  timezone: UTC

  schedule:
    monthly>: 1,09:00:00

  +depend_on_all_daily_workflow_in_month:
    loop>: ${moment(last_session_time).daysInMonth()}
    _do:
      require>: daily_workflow
      session_time: ${moment(last_session_time).add(i, 'day')}
  ```
