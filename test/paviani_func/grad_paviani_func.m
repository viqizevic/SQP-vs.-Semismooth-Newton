function g = grad_paviani_func(x)
	g = approx_gradient('paviani_func',x,0.001);
end