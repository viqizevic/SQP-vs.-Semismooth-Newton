function g = grad_holzmann_func(x)
	g = approx_gradient('holzmann_func',x,0.001);
end