function g = grad_rosenbrock_func(x)
	g = approx_gradient('rosenbrock_func',x,0.001);
end