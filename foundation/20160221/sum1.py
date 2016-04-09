def cacl_sum(*args):
    sum = 0
    for n in args:
        sum = sum + n
    return sum


result = cacl_sum(1, 2, 3, 4, 5, 6)
print(result)
