# async-intro


## Outline

- Multiple repls
- Channels
- <!! and >!!
- Buffered channels
- put! and take!
- put! and take! callbacks
- challenge: implement >!! and <!!
- timeout
- alts!!
- alts + timeout = timed waits
- close!
- closed channels yield nil
- 















More notes below, but don't look at them! You'll learn more by writing
the code yourself.











## Notes

### Multiple Repls

To understand concurrency tools, I find it helpful to run two (or
more) REPLs connnected to the same Clojure process.

- Start the first REPL normally (eg: C-c M-j)
- Run (nrepl/start-server :port 7888)
- M-c cider-connect to 7888
-

### Basics of Channels

```
(def c1 (async/chan))
(>!! c1 "Hello")
```

In other repl:

```
(<!! c1)
```

### Capacity

Channels have a capacity:

```
(def c2 (async/chan 3))
(<!! c2 "one")
(<!! c3 "two")
...
```

Exercise:

Is `(chan)` the same as `(chan 1)`?


### Puts and Takes

```
(def c1 (chan))
(async/put! c1 "Hello")
```

```
(async/take! c1 (fn [v] (println v)))
```

Excercise:

put! also takes a callback. What does it do? What gets passed to it?


### Puts and takes are async

```
(def c (chan 3))

(dotimes [i 5]
  (async/put! c i))

```

```
(async/take! c (fn [val]
                 
```

### Close

Channels can be closed

```
(def c (chan))
(doseq [i (range 5)]
  (println "putting:" i)
  (>!! c i))
```

```
(<!! c)
```

### Timeouts


### Alts

```
```

```
```

## Go blocks

```
```


### Dynamic vars

What do you think will get printed, in what order?

```
(def ^:dynamic dynavar 1)
(binding [dynavar 77]
  (println "entering binding scope")
  (go
    (println "starting go block")
    (<! (async/timeout 1000))
    (println "leaving go block, dynavar is:" dynavar))
  (println "leaving binding scope"))
```


### Other clojure concurrency constructs

#### Atoms

```
(defn slow-inc [v]
  (println "starting slow-inc of" v)
  (Thread/sleep 5000)
  (let [new-v (inc v)]
    (println "returing" new-v)
    new-v))
```

Run (swap! an-atom slow-inc) in two repls simultaneously. What do you
think will be printed, in what order?

#### Delays

```
(def d (delay (slow-inc 99)))
```

Run `@d` in two repls. What do you think will be printed?

#### Promises

Create, deref in repl 1, deliver in repl 2.


#### Queues

No special queue functions in clojure - just use java.util.concurrent.
