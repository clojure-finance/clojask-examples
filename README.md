## Clojask Examples

Some commonly used examples for Clojure parallel data processing framework [Clojask](https://github.com/clojure-finance/clojask).

### Usage

The example codes are stored in `src/clojask_examples`.

Change the value of `:main` in `project.clj` to the corresponding namespace of the example you want to run.

Run the example using 

```bash
lein run
```

### List of Examples

- [Basic tutorial](src/clojask_examples/basic_tutorial.clj)

  Cover the basic APIs in the Clojask library. Also cover how to read and write to different file formats.


- [Groupby Aggregate](src/clojask_examples/groupby_aggregate.clj)

  Group-by, then aggregate v.s. direct aggregate.

- [Join](src/clojask_examples/ordinary_join.clj)

  Natural inner join, left join and right join.

- [Rolling Join](src/clojask_examples/rolling_join.clj)

  Forward and backward rolling join with thresholds. See the definition [here](https://www.r-bloggers.com/2016/06/understanding-data-table-rolling-joins/).

- [Enhanced Reshape](src/clojask_examples/enhanced_reshape.clj)

  Cbind, rbind, melt and dcast. See the [definition](https://cran.r-project.org/web/packages/data.table/vignettes/datatable-reshape.html#enhanced-new-functionality) of them in R.

- [Timezone](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/timezone.clj)

  How to define parsers and formatters for fields of zoned datetime.

- [Outer Join](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/outer_join.clj)

  How to do Outer Join / Cartesian Product.

