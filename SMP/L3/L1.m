%sim
games_to_play = 10000;
times_ace_first = 0;
times_ten_after_ace = 0;
blackjack = 0;
bust_counter = 0;
risk_averse = 7.5;
risk_neutral = 6.5;
risky = 4.5;
avg_card_value = 6.5;
stop_counter = 0;
bust_strat_count = 0;
j = 0;
dealer_bust = 0;
dealer_win = 0;

for n=1:games_to_play
    cards = shufflecards()
    if cards(1).value == 1
        times_ace_first = times_ace_first+1;
        if cards(2).value == 10
            blackjack = blackjack + 1;
            times_ten_after_ace = times_ten_after_ace +1;
        end
    end
    if cards(1).value == 10
        if cards(2).value == 1
            blackjack = blackjack+1;
        end
    end
    if (cards(1).value + cards(2).value + cards(3).value) > 21
        bust_counter = bust_counter +1;
    end
    
    hand_value = cards(1).value + cards(2).value;
    for i=3:52
        if (hand_value + avg_card_value) < 21
            hand_value = hand_value + cards(i).value;
            if hand_value > 21
                bust_strat_count = bust_strat_count +1;
                hand_value = 0;
                j = i;
                break
            end
        else
            stop_counter = stop_counter +1;
            j = i;
            break
        end
    end
    
    dealer_hand = cards(j+1).value + cards(j+2).value;
    for k=j+3:52
        if dealer_hand < hand_value
            dealer_hand = dealer_hand + cards(k).value;
        end
        if dealer_hand > 21
            dealer_bust = dealer_bust+1;
            break
        end
        if dealer_hand >= hand_value
            dealer_win = dealer_win +1;
            break
        end
    end
end

est_ace_first = times_ace_first/games_to_play;
theo_ace_first = 4/52;

est_ten_after_ace = times_ten_after_ace / times_ace_first;
theo_ten_after_ace = 16/51;

est_blackjack = blackjack / games_to_play;
theo_blackjack = 2*((4/51) * (16/52));

est_bust = bust_counter / games_to_play;

est_bust_strat = bust_strat_count / games_to_play;

est_player_win = dealer_bust / games_to_play;

