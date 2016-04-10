def f(a):
    return a*a

list1=[1,2,3,4,5,6]
print(list1)
list2=map(f,list1)
print(list2)
list3=list(list2)
print(list3)

#匿名函数
list4=list(map(lambda x: x * x, list3))
print(list4)