function H = hess_holzmann_func_v0(x)
	lambda = 0.00001;
	H = hess_holzmann_func(x) + lambda*eye(length(x));
end