function H = hess_rosenbrock_func_v0(x)
	lambda = 0.000000001;
	H = hess_rosenbrock_func(x) + lambda*eye(length(x));
end