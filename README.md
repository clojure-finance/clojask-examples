## Clojask Examples

Some commonly used examples for Clojure parallel large dataframe [Clojask](https://github.com/clojure-finance/clojask)

### Usage

The example codes are stored in `src/clojask_examples`.

Change the value of `:main` in `project.clj` to the corresponding namespace of the example you want to run.

Run the example using 

```bash
lein run
```

### List of Examples

- [Ordinary join](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/ordinary_join.clj)

  Normal inner join, left join and right join. Note that outter joins are not supported in ***Clojask***.

- [Rolling join](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/rolling_join.clj)

  Forward and backward rolling join with thresholds

- [Timezone](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/timezone.clj)

  How to define parsers and formatters for fields of type zoned datetime

  
