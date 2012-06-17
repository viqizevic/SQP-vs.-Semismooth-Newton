%problem_AG_opt_ctrl
%
function y = func_for_problem_AG_opt_ctrl(x)
	Q = opt_ctrl_prob_func_H(0.2,12,50);
	q = opt_ctrl_prob_func_q(0.1,1.5,12,50);
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end