function g = grad_rosenbrock_func_v0(x)
	lambda = 0.001;
	g = grad_rosenbrock_func(x) + lambda*x;
end