function H = hess_lin_regression_func_v0(x)
	lambda = 0.001;
	H = hess_lin_regression_func(x) + lambda*eye(length(x));
end