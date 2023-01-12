import functools
list2 = [1,2,3,3,5]
m = functools.reduce(lambda x,y:x+y,list2,5)
print(m)
import operator
def accumulate(iterable, func=operator.add, *, initial=None):
    'Return running totals'
    # accumulate([1,2,3,4,5]) --> 1 3 6 10 15
    # accumulate([1,2,3,4,5], initial=100) --> 100 101 103 106 110 115
    # accumulate([1,2,3,4,5], operator.mul) --> 1 2 6 24 120
    it = iter(iterable)
    total = initial
    if initial is None:
        try:
            total = next(it)
            print(total)
        except StopIteration:
            return
    yield total
    for element in it:
        print(element,total)
        total = func(total, element)
        yield total

list2 = [1,2,3,4,6]
print(list(accumulate(list2)))
