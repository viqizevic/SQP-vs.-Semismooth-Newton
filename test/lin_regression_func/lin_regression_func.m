function y = lin_regression_func(x)
	eta = [-8; -7; -6; -5; -2; 1; 5; 12; 17; 25];
	xi = [1:1:10]';
	y = 0;
	m = length(xi);
	for k=1:m
		y = y + ( x(1)*xi(k) + x(2) - eta(k) )^2;
	end
end