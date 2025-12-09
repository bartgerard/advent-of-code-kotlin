# Test 1

```text
## #-#
|#-# |
#----#
```

```text
## #-#
|OOOOO
#OOOOO
```

```text
-> 15 = (1,1) to (5,2) == [1..5 to 1..2]
```

```kotlin
corners.contains(Point2l(1, 1)) && corners.contains(Point2l(5, 2))
```

# Test 2

```text
 ## #-#
##| | |
| #-# |
#-----#
```

```text
 ## #-#
##| | |
| #-# |
#-----#
```

# Test 3

```text
 ###--#
##||  |
| ##  |
#-----#
```

# Test 4

```text
 ####
##|||
| ##|
#---#
```