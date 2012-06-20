%problem_A_example_16_2_nocedal_wright
%(Vgl. Beispiel 16.2 in \cite[S.~452]{nocedal})
%\min_{x\in\R^3}\ 3 x_1^2 + 2 x_1 x_2 + x_1 x_3 + 2.5 x_2^2 + 2 x_2 x_3 + 2 x_3^2 - 8 x_1 - 3 x_2 - 3 x_3
%\nb x_1 + x_3 & = 3 \\
%x_2 + x_3 & = 0 \\
%
function y = func_for_problem_A_example_16_2_nocedal_wright(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	q = [-8; -3; -3];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end