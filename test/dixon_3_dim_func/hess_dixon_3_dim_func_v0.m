function H = hess_dixon_3_dim_func_v0(x)
	lambda = 0.0000000001;
	H = hess_dixon_3_dim_func(x) + lambda*eye(length(x));
end