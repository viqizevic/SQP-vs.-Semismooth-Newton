function g = grad_nichtlin_regression_func_v0(x)
	lambda = 0.001;
	g = grad_nichtlin_regression_func(x) + lambda*x;
end