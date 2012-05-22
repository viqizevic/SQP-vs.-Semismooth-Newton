function g = grad_func_for_problem_A_example_16_2_nocedal_wright(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	c = 0;
	q = [-8; -3; -3];
	g = Q*x + q;
end