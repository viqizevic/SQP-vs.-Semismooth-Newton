function y = quad_regression_func_v0(x)
	lambda = 0.001;
	y = quad_regression_func(x) + (lambda/2)*norm(x)^2;
end