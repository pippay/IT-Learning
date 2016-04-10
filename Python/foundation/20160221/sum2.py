def cacl_sum(*args):
    def sum():
        p = 0
        for n in args:
            p = p + n
        return p
    return sum

result=cacl_sum(1, 2, 3, 4, 5, 6)
print(result)
result2=result()
print(result2)