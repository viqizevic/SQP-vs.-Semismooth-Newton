function y = lin_regression_func(x)
	eta = [-0.5; -2; -3; -3; -2.5; -2; -1; 1; 3; 5.5];
	xi = [1:1:10]';
	y = 0;
	m = length(xi);
	for k=1:m
		y = y + ( x(1)*xi(k) + x(2) - eta(k) )^2;
	end
end