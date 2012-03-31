function H = hess_quad_func_2(x)
	xd = [4; 7];
	H = approx_hessian('quad_func_2',x,0.001);
end