% plot3dim(f,a,b,gap)
% - the function f should be a scalarfunction f:R^2->R
% - a,b real numbers with a < b
% - gap a real number with gap << (b-a)
% the plot will be made considering all points (x,y):
% x in {a, a+gap, a+2*gap, ... , b},
% y in {a, a+gap, a+2*gap, ... , b}
% 
function plot3dim(f,a,b,gap)
	vx = a:gap:b;
	vy = vx;
	[x,y] = meshgrid(vx,vy);
	[m,n] = size(x);
	for k=1:n
		for l=1:m
			v = [x(k,l); y(k,l)];
			z(k,l) = feval(f,v);
		end
	end
	surfc(x,y,z);
end