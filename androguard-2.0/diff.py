s1 = set(open('a.txt','r').readlines())
s2 = set(open('b.txt','r').readlines())
s_result = s1.difference(s2)#.union(s2.difference(s1))
print 'length of the difference from right to bad:%d'%len(s_result)
for i in s_result:
   print i
s_second_result = s2.difference(s1)
print 'length of the difference from bad to right:%d'%len(s_second_result)
for i in s_second_result:
   print i
#print 'dif: %s'%(s1.difference(s2).union(s2.difference(s1)))

