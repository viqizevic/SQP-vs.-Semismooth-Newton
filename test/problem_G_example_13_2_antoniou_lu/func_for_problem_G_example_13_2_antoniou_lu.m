%problem_G_example_13_2_antoniou_lu
%(Vgl. Beispiel 13.2 in \cite[S.~415f]{antoniou})
%\min_{x\in\R^4}\ (x_1 - x_3)^2 + (x_2 - x_4)^2
%\nb -x_1 & \leq 0 \\
%-x_2 & \leq 0 \\
%x_1 + 2 x_2 & \leq 2 \\
%-x_4 & \leq -2 \\
%-x_3 - x_4 & \leq -3 \\
%x_3 + 2 x_4 & \leq 6 \\
%
function y = func_for_problem_G_example_13_2_antoniou_lu(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	q = [0; 0; 0; 0];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end