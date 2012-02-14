function H = hess_rosenbrock_func_v0(x)
	lambda = 0.00001;
	H = hess_rosenbrock_func(x) + lambda*eye(length(x));
end