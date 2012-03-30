function H = hess_dixon_func_1_v0(x)
	lambda = 0.001;
	H = hess_dixon_func_1(x) + lambda*eye(length(x));
end