function H = hess_bazaraa_shetty_func_v0(x)
	lambda = 0.000000001;
	H = hess_bazaraa_shetty_func(x) + lambda*eye(length(x));
end