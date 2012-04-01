function H = hess_betts_func_v0(x)
	lambda = 0.001;
	H = hess_betts_func(x) + lambda*eye(length(x));
end