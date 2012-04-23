function g = grad_lin_regression_func_v0(x)
	lambda = 0.001;
	g = grad_lin_regression_func(x) + lambda*x;
end