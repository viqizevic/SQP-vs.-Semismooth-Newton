function H = hess_dixon_func_2_v0(x)
	lambda = 0.001;
	H = hess_dixon_func_2(x) + lambda*eye(length(x));
end