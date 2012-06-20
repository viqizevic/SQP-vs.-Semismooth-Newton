% plot3dim(f,x1,x2,gapx,y1,y2,gapy)
% - the function f should be a scalarfunction f:R^2->R
% - x1,x2 real numbers with x1 < x2
% - gapx a real number with gapx << (x2-x1)
% - y1,y2 real numbers with y1 < y2
% - gapy a real number with gapy << (y2-y1)
% the plot will be made considering all points (x,y):
% x in {x1, x1+gapx, x1+2*gapx, ... , x2},
% y in {y1, y1+gapy, y1+2*gapy, ... , y2}
% 
function plot3dim(f,x1,x2,gapx,y1,y2,gapy)
	vx = x1:gapx:x2;
	vy = y1:gapy:y2;
	[x,y] = meshgrid(vx,vy);
	[m,n] = size(x);
	z = zeros(m,n);
	for k=1:m
		for l=1:n
			v = [x(k,l); y(k,l)];
			z(k,l) = feval(f,v);
		end
	end
	surfc(x,y,z);
end