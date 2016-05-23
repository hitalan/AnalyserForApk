s1 = set(open('a.txt','r').readlines())
s2 = set(open('b.txt','r').readlines())

#print 'ins: %s'%(s1.intersection(s2))
#print 'uni: %s'%(s1.union(s2))
print 'dif: %s'%(s1.difference(s2).union(s2.difference(s1)))

