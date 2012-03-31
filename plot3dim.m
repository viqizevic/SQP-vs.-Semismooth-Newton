% plot3dim(f,x1,x2,y1,y2,gap)
% - the function f should be a scalarfunction f:R^2->R
% - x1, x2 real numbers with x1 < x2
% - y1, y2 real numbers with y1 < y2
% - gap a real number with gap << min((x2-x1),(y2-y1))
% the plot will be made considering all points (x,y):
% x in {x1, x1+gap, x1+2*gap, ... , x2},
% y in {y1, y1+gap, y1+2*gap, ... , y2}
% 
function plot3dim(f,x1,x2,y1,y2,gap)
	vx = x1:gap:x2;
	vy = y1:gap:y2;
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