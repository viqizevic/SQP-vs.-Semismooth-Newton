function y = holzmann_func(x)
	y = 0;
	for k=1:99
		u = 25 + (-50*log(0.01*k))^(2/3);
		y = y + ( -0.1*k + exp( -(1/x(1)) * ( u - x(2) )^x(3) ) )^2;
	end
end