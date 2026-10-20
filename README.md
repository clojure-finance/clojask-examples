## Clojask Examples

Some commonly used examples for Clojure parallel data processing framework [Clojask](https://github.com/clojure-finance/clojask).

### Usage

The example codes are stored in `src/clojask_examples`.

Change the value of `:main` in `project.clj` to the corresponding namespace of the example you want to run.

Run the example using 

```bash
lein run
```

To run every example and check its outputs, use

```bash
lein test
```

### List of Examples

- [Basic tutorial](src/clojask_examples/basic_tutorial.clj)

  Cover the basic APIs in the Clojask library. Also cover how to read and write to different file formats.


- [Groupby Aggregate](src/clojask_examples/groupby_aggregate.clj)

  Group-by, then aggregate v.s. direct aggregate.

- [Join](src/clojask_examples/ordinary_join.clj)

  Natural inner join, left join and right join.

- [Store Results in memory](src/clojask_examples/in_memory.clj)

  For datasets that are smaller than memory, you can store the result in memory and reuse it faster. Also shows reading an Excel file with clojask-io's `read-excel` and writing the result to an `.xlsx` file straight from `compute`.

- [Connection with `tech.ml.dataset`](src/clojask_examples/techmldataset.clj)

  Convert from and to the popular Clojure DataFrame library `tech.ml.dataset`.

- [Rolling Join](src/clojask_examples/rolling_join.clj)

  Forward and backward rolling join with thresholds. See the definition [here](https://www.r-bloggers.com/2016/06/understanding-data-table-rolling-joins/).

- [Enhanced Reshape](src/clojask_examples/enhanced_reshape.clj)

  Cbind, rbind, melt and dcast. See the [definition](https://cran.r-project.org/web/packages/data.table/vignettes/datatable-reshape.html#enhanced-new-functionality) of them in R.

- [Timezone](src/clojask_examples/timezone.clj)

  How to define parsers and formatters for fields of zoned datetime.

- [Outer Join](src/clojask_examples/outer_join.clj)

  Full outer join: keeps the rows of both dataframes, matched or not.

