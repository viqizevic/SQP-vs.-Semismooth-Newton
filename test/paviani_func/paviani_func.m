function y = paviani_func(x)
	y = 0;
	u = 1;
	for k=1:10
		y = y + ( (log(x(k)-2))^2 + (log(10-x(k)))^2 );
		u = u*x(k);
	end
	y = y - u^2;
end