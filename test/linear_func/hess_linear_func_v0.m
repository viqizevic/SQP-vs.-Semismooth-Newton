function H = hess_linear_func_v0(x)
	lambda = 0.0001;
	H = hess_linear_func(x) + lambda*eye(length(x));
end