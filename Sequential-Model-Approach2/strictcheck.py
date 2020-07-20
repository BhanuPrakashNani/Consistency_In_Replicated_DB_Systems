f = open('Writers.log', 'r')

l = f.readlines()

p1 = []
p2 = []
p3 = []
p4 = []
p5 = []

items = []
for i in l:
  items = list(i.split(' '))


  if(str(items[0]) == "P1:"):
    p1.append(i)

  if(str(items[0]) == 'P2:'):
    p2.append(i)

  if(str(items[0]) == 'P3:'):
    p3.append(i)

  if(str(items[0]) == 'P4:'):
    p4.append(i)

  if(str(items[0]) == 'P5:'):
    p5.append(i)


print(p1[1], p2[2])
