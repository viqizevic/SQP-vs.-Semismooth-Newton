function g = grad_rosenbrock_func_1(x)
	g = approx_gradient('rosenbrock_func_1',x,0.001);
end