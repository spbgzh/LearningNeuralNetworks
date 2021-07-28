clf();
i=2;
t = read ('C:\Users\291300\Desktop\x.txt',1,100);
tl = read ('C:\Users\291300\Desktop\y.txt',1,100);
x=[-2:0.04:2]
plot(t,tl,'k+', x, sin(%pi*i/4*x)+1,'k');
