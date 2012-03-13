function y = dixon_func_1(x)
	n = 10;
	y = (1-x(1))^2;
	for k=1:(n-1)
		y = y + (x(k)^2-x(k+1))^2;
	end
	y = y + (1-x(n))^2;
end