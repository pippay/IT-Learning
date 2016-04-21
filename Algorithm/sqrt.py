import math

a = 2 ** 0.5
print(a)
b = math.sqrt(2)
print(b)
c = math.pow(2, 0.5)
print(c)

def sqrt_1(num, x):
    result = (x + num / x) / 2
    if math.fabs(result - b) < 0.001:
        return result
    else:
        return sqrt_1(num, result)

print(sqrt_1(2, 1))
print(sqrt_1(2, 2))
print(sqrt_1(2, 3))

def sqrt_2(left, right, num):
    middle = (left + right) / 2
    if middle - num > 0.001:
        return sqrt_2(left, middle, num)
    elif middle - num < -0.001:
        return sqrt_2(middle, right, num)
    else:
        return middle

print(sqrt_2(1, 2, b))
