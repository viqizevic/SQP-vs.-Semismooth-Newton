function H = hess_rosenbrock_func_1(x)
	H = approx_hessian('rosenbrock_func_1',x,0.001);
end