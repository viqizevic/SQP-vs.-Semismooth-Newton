%problem_v_exp_1
%(Exponent-Funktion)
%\min_{x\in\R^3}\ e^{\|x\|^2}
%\nb 0.5 \leq x_1 & \leq 10 \\
%1 \leq x_2 & \leq 10 \\
%1 \leq x_3 & \leq 10 \\
%
function y = func_for_problem_v_exp_1(x)
	y = exp(norm(x)^2);
end