function y = func_for_problem_G_example_with_diamond_area(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end