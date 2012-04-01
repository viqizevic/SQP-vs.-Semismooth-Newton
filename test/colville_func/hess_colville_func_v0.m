function H = hess_colville_func_v0(x)
	lambda = 0.001;
	H = hess_colville_func(x) + lambda*eye(length(x));
end