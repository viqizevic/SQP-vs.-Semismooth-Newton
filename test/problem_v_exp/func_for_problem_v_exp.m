%problem_v_exp
%(Exponent-Funktion)
%\min_{x\in\R^2}\ e^{\|x\|^2}
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_exp(x)
	y = exp(norm(x)^2);
end